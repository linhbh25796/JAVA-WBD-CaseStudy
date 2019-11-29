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
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView listEmployee(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageSize == null) pageSize = 5;
        if (page == null) page = 0;
        List<EmployeeDetail> listEmployee;
        int totalRecord = employeeDAO.getListEmployeeLength();
        listEmployee = employeeDAO.getListEmployee(page, pageSize); // trang hien tai dung show
        //System.out.println(">>> total record:" + totalRecord);

        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("totalPages", totalRecord / pageSize + 1);  //tong so trang de quan ly 1 2 3 4
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("employeeList", listEmployee);
        modelAndView.addObject("searchList", new Employee()); //rong
        return modelAndView;
    }

    /* -- back up
     @PostMapping("/employee-list-search")
     public ModelAndView displaySearchList(@ModelAttribute("searchList") Employee employee) {


         List<EmployeeDetail> listEmployee;

         listEmployee = employeeDAO.getSearchList(employee.getDepartmentOfEmployee()); // trang hien tai dung show
         System.out.println(">>> total record:");
         //listEmployee = employeeDAO.getSearchList(employee.getDepartmentOfEmployee(),);
         //EmployeeDetail employeeDetail = new EmployeeDetail();

         ModelAndView modelAndView = new ModelAndView("/employee/list");
         modelAndView.addObject("employeeList", listEmployee);
         return modelAndView;
     }*/

    @PostMapping("/employee-list-search")
    public ModelAndView displaySearchList(@ModelAttribute("searchList") Employee employee,
                                          @RequestParam(value = "page", required = false) Integer page,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageSize == null) pageSize = 5;
        if (page == null) page = 0;

        List<EmployeeDetail> listEmployee;
        int totalRecord = employeeDAO.getListEmployeeLength();


        listEmployee = employeeDAO.getSearchList(employee.getDepartmentOfEmployee(),page,pageSize); // trang hien tai dung show
        //System.out.println(">>> total record:");
        //listEmployee = employeeDAO.getSearchList(employee.getDepartmentOfEmployee(),);
        //EmployeeDetail employeeDetail = new EmployeeDetail();

        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("totalPages", totalRecord / pageSize + 1);  //tong so trang de quan ly 1 2 3 4
        modelAndView.addObject("pageSize", pageSize);
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
        MultipartFile multipartFile = employeeForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();
        try {

            FileCopyUtils.copy(employeeForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Employee employeeObject = new Employee(
                employeeForm.getName(), employeeForm.getBirthDate(),
                employeeForm.getAddress(), fileName,
                employeeForm.getSalary(), employeeForm.getDepartmentOfEmployee()
        );
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
    public ModelAndView updateCustomer(@ModelAttribute("employee") EmployeeForm employeeForm, BindingResult result) {
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

        employeeService.edit(employeeForm, fileName);
        ModelAndView modelAndView = new ModelAndView("/employee/edit");
        modelAndView.addObject("employee", employeeForm);
        modelAndView.addObject("message", "Đã sửa!!");
        return modelAndView;

    }

    //Delete employee
    @GetMapping("/delete-employee/{id}") // path variable = bien nam tren url (path)
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


}
