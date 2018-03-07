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
 * 控制层
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
	 * 人员信息删除方法
	 * @param id 主键Id
	 * @return
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id){
		employeeService.delete(id);
		return "redirect:/emps";
	}
	
	/**
	 * 通过Id 获取人员信息
	 * @param id 主键Id
	 * @param map map集合对象
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
	 * 人员信息修改方法
	 * @param employee
	 * @return
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.PUT)
	public String update(/*@RequestParam("createTime") String createTime,*/Employee employee){
		/**
		 * @PathVariable出现点号"."时导致路径参数截断获取不全的解决办法
		 * 1.在version段后增加一个静态的字符段，这个段没有任何意义，可以为任意字符。
		 * URL:http://host_ip/consumer/appVersion/phone/android/download/{version}/static_str
		 * 
		 * 2.在@RequestMapping的value中使用SpEL来表示，value中的{version}换成{version:.+}。
		 * @RequestMapping(value="android/download/{version:.+}",method=RequestMethod.GET)
		 */
		
		System.out.println("修改方法");
		//System.out.println("createTime:"+createTime);
		System.out.println(employee.getCreateTime());
		//System.out.println("修改方法+createTime"+createTime);
		/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(createTime, pos);
		employee.setCreateTime(currentTime_2);*/
		employeeService.save(employee);
		return "redirect:/emps";
	}
	
	/**
	 * 人员信息保存方法
	 * @param employee
	 * @return
	 */
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	public String save(Employee employee){
		System.out.println("添加方法");
		employeeService.save(employee);
		return "redirect:/emps";
	}
	
	/**
	 * ajax 异步验证 姓名是否重复
	 * @param lastName 姓名
	 * @return 返回字符串
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
	 * list页面中，点击编辑方法，进入input编辑页面 ，把人员信息和部门信息都传递过去
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
	 * 在index页面，点击超链接 进入input新增页面，并且把部门的所有值传递到下拉框
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
	 * 分页
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
			//对 pageNo 的校验
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
