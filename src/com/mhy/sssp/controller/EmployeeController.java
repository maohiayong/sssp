package com.mhy.sssp.controller;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mhy.sssp.entity.Employee; 
import com.mhy.sssp.service.DepartmentService;
import com.mhy.sssp.service.EmployeeService;

/**
 * ���Ʋ�
 * @author mhy
 *
 */
@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * ��Ա��Ϣɾ������
	 * @param id ����Id
	 * @return
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id){
		employeeService.delete(id);
		return "redirect:/emps";
	}
	
	/**
	 * ͨ��Id ��ȡ��Ա��Ϣ
	 * @param id ����Id
	 * @param map map���϶���
	 */
	/*@ModelAttribute
	public void getEmployee(@RequestParam(value="id",required=false) Integer id,Map<String,Object> map){
		if(id!=null){
			Employee employee=employeeService.get(id);
			employee.setDepartment(null);
			
			map.put("employee", employee);
		}
		
	}*/
	
	/**
	 * ��Ա��Ϣ�޸ķ���
	 * @param employee
	 * @return
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.PUT)
	public String update(/*@RequestParam("createTime") String createTime,*/Employee employee){
		/**
		 * @PathVariable���ֵ��"."ʱ����·�������ضϻ�ȡ��ȫ�Ľ���취
		 * 1.��version�κ�����һ����̬���ַ��Σ������û���κ����壬����Ϊ�����ַ���
		 * URL:http://host_ip/consumer/appVersion/phone/android/download/{version}/static_str
		 * 
		 * 2.��@RequestMapping��value��ʹ��SpEL����ʾ��value�е�{version}����{version:.+}��
		 * @RequestMapping(value="android/download/{version:.+}",method=RequestMethod.GET)
		 */
		
		System.out.println("�޸ķ���");
		//System.out.println("createTime:"+createTime);
		System.out.println(employee.getCreateTime());
		//System.out.println("�޸ķ���+createTime"+createTime);
		/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(createTime, pos);
		employee.setCreateTime(currentTime_2);*/
		employeeService.save(employee);
		return "redirect:/emps";
	}
	
	/**
	 * ��Ա��Ϣ���淽��
	 * @param employee
	 * @return
	 */
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	public String save(Employee employee){
		System.out.println("��ӷ���");
		employeeService.save(employee);
		return "redirect:/emps";
	}
	
	/**
	 * ajax �첽��֤ �����Ƿ��ظ�
	 * @param lastName ����
	 * @return �����ַ���
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxValidateLastName",method=RequestMethod.POST)
	public String validateLastName(@RequestParam(value="lastName",required=true)String lastName){
		Employee employee=employeeService.getByLastName(lastName);
		if(employee==null){
			return "0";
		}else{
			return "1";
		} 
	}
	
	/**
	 * listҳ���У�����༭����������input�༭ҳ�� ������Ա��Ϣ�Ͳ�����Ϣ�����ݹ�ȥ
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	public String input(@PathVariable("id") Integer id,Map<String,Object> map){
		if(id!=0){
		   Employee employee=employeeService.get(id);
		   map.put("employee", employee);
		   map.put("departments", departmentService.getAll());
		}
		
		return "emp/input";
	}
	
	/**
	 * ��indexҳ�棬��������� ����input����ҳ�棬���ҰѲ��ŵ�����ֵ���ݵ�������
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/emp",method=RequestMethod.GET)
	public String input(Map<String,Object> map){
		map.put("departments", departmentService.getAll());
		map.put("employee", new Employee());
		return "emp/input";
	}
	
	/**
	 * ��ҳ
	 * @param pageNoStr
	 * @param map
	 * @return
	 */
	@RequestMapping("/emps")
	public String list(
			@RequestParam(value="pageNo",required=false,defaultValue="1")String pageNoStr,
			Map<String,Object> map){
		int pageNo=1;
		try {
			//�� pageNo ��У��
			pageNo = Integer.parseInt(pageNoStr);
			if(pageNo < 1){
				pageNo = 1;
			}
		} catch (Exception e) {}
		
		Page<Employee> page=employeeService.getPage(pageNo, 5);
		map.put("page", page);
		return "emp/list";
	}
}
