package com.mhy.sssp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mhy.sssp.entity.Employee;
import com.mhy.sssp.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Transactional
	public void delete(Integer id){
		employeeRepository.delete(id);
	}
	
	@Transactional(readOnly=true)
	public Employee get(Integer id){
		return employeeRepository.findOne(id);
	}
	
	/**
	 * �����Ա��Ϣ
	 * @param employee ��Ա����
	 */
	@Transactional
	public void save(Employee employee){
		if(employee.getId()==null){
			employee.setCreateTime(new Date());
		}
		employeeRepository.saveAndFlush(employee);
	}
	
	/**
	 * ͨ�� ������ȡ��Ա��Ϣ
	 * @param lastName ����
	 * @return ����
	 */
	@Transactional(readOnly=true)
	public Employee getByLastName(String lastName){
		return employeeRepository.getByLastName(lastName);
	}
	
	/**
	 * û�в�ѯ�����ķ�ҳ
	 * @param pageNo ҳ��
	 * @param pageSize ÿҳ�Ĵ�С
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Employee> getPage(int pageNo, int pageSize){
		PageRequest pageable = new PageRequest(pageNo - 1, pageSize);
		return employeeRepository.findAll(pageable);
	}
}
