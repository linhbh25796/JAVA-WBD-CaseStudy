package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/department-list")
    public ModelAndView listDepartment() {
        Iterable<Department> departments = departmentService.findAll();
        ModelAndView modelAndView = new ModelAndView("/department/list");
        modelAndView.addObject("departmentList", departments);
        return modelAndView;
    }

    //Create Department
    @GetMapping("/create-department")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("department/create");
        modelAndView.addObject("department", new Department());
        return modelAndView;
    }

    @PostMapping("/create-department")
    public ModelAndView saveDepartment(@ModelAttribute("department") Department department) {
        departmentService.save(department);
        ModelAndView modelAndView = new ModelAndView("department/create");
        modelAndView.addObject("department", new Department());
        modelAndView.addObject("message", "Đã thêm mới  !!");
        return modelAndView;
    }

    //Delete Department
    @GetMapping("/delete-department/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Department department = departmentService.findById(id);

        if (department != null) {
            ModelAndView modelAndView = new ModelAndView("department/delete");
            modelAndView.addObject("department", department);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("/error.404");
        return modelAndView;
    }

    @PostMapping("/delete-department")
    public String deleteProvince(@ModelAttribute("department") Department department) {
        departmentService.remove(department.getId());
        return "redirect:department-list";
    }

    //Edit Department
    @GetMapping("/edit-department/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Department department = departmentService.findById(id);
        if (department != null) {
            ModelAndView modelAndView = new ModelAndView("/department/edit");
            modelAndView.addObject("department", department);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-department")
    public ModelAndView updateDepartment(@ModelAttribute("department") Department department){
        departmentService.save(department);
        ModelAndView modelAndView = new ModelAndView("/department/edit");
        modelAndView.addObject("department", department);
        modelAndView.addObject("message","Đã sửa");
        return modelAndView;
    }



}
