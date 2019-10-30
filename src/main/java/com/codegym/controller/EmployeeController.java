package com.codegym.controller;

import com.codegym.entity.EmployeeDetail;
import com.codegym.entity.EmployeeForm;
import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.repository.EmployeeDAO;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@PropertySource("classpath:global_config_app.properties")
public class EmployeeController {
    @Autowired
    Environment env;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeDAO employeeDAO;

    @ModelAttribute("departments")
    public Iterable<Department> departments() {
        return departmentService.findAll();
    }

    @GetMapping("/employee-list")
    public ModelAndView listEmployee(Pageable pageable) {
        List<EmployeeDetail> listEmployee;
        listEmployee = employeeDAO.getListEmployee(pageable);
        System.out.println(">>> list employee:" + listEmployee);
        EmployeeDetail employeeDetail = new EmployeeDetail();
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employeeList", listEmployee);
        return modelAndView;
    }

    //Create employee
    @GetMapping("/create-employee")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("employee/create");
        modelAndView.addObject("employee", new Employee());
        return modelAndView;
    }


    @PostMapping("/create-employee")
    public ModelAndView saveEmployee(@ModelAttribute("employeeform") EmployeeForm employeeForm, BindingResult result) {

        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("employee/validateError");
            modelAndView.addObject("errorMessage", "Nhập đúng định dạng mm-dd-yyyy");
            System.out.println("Result Error Occurred" + result.getAllErrors());
            return modelAndView;
        }
        // lay ten file
        MultipartFile multipartFile = employeeForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        // luu file len server
        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(employeeForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        // tao doi tuong de luu vao db


        Employee employeeObject = new Employee(
                employeeForm.getName(), employeeForm.getBirthDate(),
                employeeForm.getAddress(), fileName,
                employeeForm.getSalary(), employeeForm.getDepartmentOfEmployee()
        );

        // luu vao db
        //productService.save(productObject);

        employeeService.save(employeeObject);
        ModelAndView modelAndView = new ModelAndView("employee/create");
        modelAndView.addObject("employee", new EmployeeForm());
        modelAndView.addObject("message", "Đã thêm mới nhân viên");
        return modelAndView;
    }

    //Edit-employee
    @GetMapping("/edit-employee/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            ModelAndView modelAndView = new ModelAndView("/employee/edit");
            modelAndView.addObject("employee", employee);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-employee")
    public ModelAndView updateCustomer(
            @ModelAttribute("employee") EmployeeForm employeeForm,
            BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("employee/validateError");
            modelAndView.addObject("errorMessage", "Nhập đúng định dạng mm-dd-yyyy");
            System.out.println("Result Error Occurred" + result.getAllErrors());
            return modelAndView;
        }
        // lay ten file
        MultipartFile multipartFile = employeeForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        // luu file len server
        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(employeeForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        // tao doi tuong de luu vao db


        Employee employeeObject = new Employee(
                employeeForm.getName(), employeeForm.getBirthDate(),
                employeeForm.getAddress(), fileName,
                employeeForm.getSalary(), employeeForm.getDepartmentOfEmployee()
        );
        employeeService.remove(employeeForm.getId());
        employeeService.save(employeeObject);
        ModelAndView modelAndView = new ModelAndView("/employee/edit");
        modelAndView.addObject("employee", employeeObject);
        modelAndView.addObject("message", "Đã sửa!!");
        return modelAndView;
       /* System.out.println(">>> employe for UPDATE:"+employee);
        employeeService.save(employee);
        ModelAndView modelAndView = new ModelAndView("/employee/edit");
        modelAndView.addObject("employee", employee);
        modelAndView.addObject("message", "Đã sửa!!");
        return modelAndView;*/
    }

    //Delete employee
    @GetMapping("/delete-employee/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            ModelAndView modelAndView = new ModelAndView("/employee/delete");
            modelAndView.addObject("employee", employee);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-employee")
    public String deleteCustomer(@ModelAttribute("employee") Employee employee) {
        employeeService.remove(employee.getId());
        return "redirect:employee-list";
    }

    //getListByPrice
    @PostMapping("/employee-list-asc")
    public ModelAndView listEmployeeByPriceASC(Pageable pageable) {
        List<EmployeeDetail> listEmployee;
        listEmployee = employeeDAO.getListByPriceASC(pageable);
        System.out.println(">>> list employee:" + listEmployee);
        EmployeeDetail employeeDetail = new EmployeeDetail();
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employeeList", listEmployee);
        return modelAndView;
    }

    @PostMapping("/employee-list-desc")
    public ModelAndView listEmployeeByPriceDESC(Pageable pageable) {
        List<EmployeeDetail> listEmployee;
        listEmployee = employeeDAO.getListByPriceDESC(pageable);
//        System.out.println(">>> list employee:"+listEmployee);
        EmployeeDetail employeeDetail = new EmployeeDetail();
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employeeList", listEmployee);
        return modelAndView;
    }


}
