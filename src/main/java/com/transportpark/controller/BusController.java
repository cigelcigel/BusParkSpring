package com.transportpark.controller;

import com.transportpark.model.domain.Bus;
import com.transportpark.model.service.BusService;
import com.transportpark.model.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BusController {
    private final FileService fileService;
    private final BusService busService;

    @GetMapping("/bus")
    public String viewBuses(Model model, @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        addPagination(model, page, size);
        model.addAttribute("bus", new Bus());
        return "/bus";
    }

    @PostMapping("/bus")
    public String addBus(Model model, @ModelAttribute Bus bus,
                         @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        Boolean identify = fileService.getIdentify();
        if (identify) {
            busService.addBus(bus);
            model.addAttribute("addedBus", bus.getModel());
        }
        model.addAttribute("identify", identify.toString());
        addPagination(model, page, size);
        return "/bus";
    }

    @GetMapping("/bus/edit/{code}")
    public String editBus(Model model, @PathVariable Integer code,
                          @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {
        model.addAttribute("editCode", code);
        addPagination(model, page, size);
        return "/bus";
    }

    @PostMapping("/bus/edit/{code}")
    public ModelAndView updateBus(Model model, @PathVariable Integer code,
                                  @RequestParam("changeMileage") Double changeMileage,
                                  @RequestParam("changeConsumption") Double changeConsumption,
                                  @RequestParam("changeDriver") Long idDriver,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        busService.changeBus(code, changeMileage, changeConsumption, idDriver);
        addPagination(model, page, size);
        return new ModelAndView("redirect:/bus");
    }

    private void addPagination(Model model, Optional<Integer> current, Optional<Integer> size) {
        int currentPage = current.orElse(1);
        int pageSize = size.orElse(10);
        Page<Bus> buses = busService.showPageList(currentPage, pageSize);
        model.addAttribute("buses", buses);
        model.addAttribute("currentPage", currentPage);
        int totalPages = buses.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
