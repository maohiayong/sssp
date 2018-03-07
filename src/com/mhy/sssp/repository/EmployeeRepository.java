package com.mhy.sssp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mhy.sssp.entity.Employee;
/**
 * jpa�ӿ�ʵ����
 * @author mhy
 *
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee getByLastName(String lastName);
}
