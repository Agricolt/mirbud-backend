package pl.mirbudpol.sklepbudowlany.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.mirbudpol.sklepbudowlany.DTO.CategoryDTO;
import pl.mirbudpol.sklepbudowlany.DTO.RatingDTO;
import pl.mirbudpol.sklepbudowlany.services.CategoryService;

import java.util.List;


@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
        public void addCategory(@Validated @RequestBody CategoryDTO dto){
        categoryService.createCategory(dto);}

    @DeleteMapping(path = "/delete/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteCategory(@PathVariable String name){
        categoryService.deleteCategory(name);}

    @GetMapping(path = "/getAll")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CategoryDTO> getCategories(){
        return categoryService.getCategories();}

    @PostMapping(path = "/update")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateCategory(@Validated @RequestBody CategoryDTO dto){
        categoryService.updateCategory(dto);}
}
