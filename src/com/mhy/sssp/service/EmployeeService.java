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
	 * 添加人员信息
	 * @param employee 人员对象
	 */
	@Transactional
	public void save(Employee employee){
		if(employee.getId()==null){
			employee.setCreateTime(new Date());
		}
		employeeRepository.saveAndFlush(employee);
	}
	
	/**
	 * 通过 姓名获取人员信息
	 * @param lastName 姓名
	 * @return 返回
	 */
	@Transactional(readOnly=true)
	public Employee getByLastName(String lastName){
		return employeeRepository.getByLastName(lastName);
	}
	
	/**
	 * 没有查询条件的分页
	 * @param pageNo 页数
	 * @param pageSize 每页的大小
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Employee> getPage(int pageNo, int pageSize){
		PageRequest pageable = new PageRequest(pageNo - 1, pageSize);
		return employeeRepository.findAll(pageable);
	}
}
