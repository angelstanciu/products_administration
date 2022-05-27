package com.example.springsecurity.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String showAllProductsAdmin(ModelMap modelMap) {
        List<Product> productList = productService.findAllProducts();
        modelMap.addAttribute("productList", productList);
        return "admin-view";
    }

    @GetMapping
    public String showAllProducts(ModelMap modelMap) {
        List<Product> productList = productService.findAllProducts();
        modelMap.addAttribute("productList", productList);
        return "user-view";
    }

    @GetMapping("admin/new")
    public String showNewProductForm(ModelMap modelMap) {
        modelMap.addAttribute("product", new Product());
        modelMap.addAttribute("pageTitle", "Add New Product");
        return "product_form";
    }

    @PostMapping("admin/save")
    public String saveProduct(Product product, RedirectAttributes redirectAttributes) {
        productService.addProduct(product);
        redirectAttributes.addFlashAttribute("message", "Product saved successfully!");
        return "redirect:/products/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
            model.addAttribute(
                    "pageTitle",
                    "Edit Product (ID " + id + ")"
            );
            return "product_form";
        } catch (NoProductFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute(
                    "message",
                    "The product has been updated successfully !"
            );
            return "redirect:/products/admin";
        }
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProductById(id);
            redirectAttributes.addFlashAttribute(
                    "message",
                    "The product has been deleted successfully !"
            );
        } catch (NoProductFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/products/admin";
    }
}
