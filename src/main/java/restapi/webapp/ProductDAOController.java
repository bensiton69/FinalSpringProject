//package restapi.webapp;
//
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.IanaLinkRelations;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@RestController
///*
// @RestController specifies that methods will inject return values
// to the body's response instead of templates that the user needs
// to render
// */
//public class ProductDAOController {
//    //    Map<String,?> unknownTypeMap;
//    private final ProductRepo productRepo;
//    private final ProductEntityAssembler productAssembler;
//
//    public ProductDAOController(ProductRepo productRepo, ProductEntityAssembler productAssembler) {
//        this.productRepo = productRepo;
//        this.productAssembler = productAssembler;
//    }
//
//    @GetMapping("/products/{id}")
//    EntityModel<Product> getSingleProduct(@PathVariable Long id){
//        Product product = productRepo.findById(id).orElseThrow(()->
//                new ProductNotFoundException(id));
//        return productAssembler.toModel(product);
//    }
//
//    @GetMapping("/products")
//    CollectionModel<EntityModel<Product>> getAllProduct(){
//
//        List<EntityModel<Product>> products =productRepo.findAll()
//                // method reference
//                .stream().map(productAssembler::toModel).collect(Collectors.toList());
//        return CollectionModel.of(products,linkTo(methodOn(ProductDAOController.class)
//                .getAllProduct()).withSelfRel());
//    }
//
//    @PostMapping("/products")
//    ResponseEntity<EntityModel<Product>> createProduct(@RequestBody Product newProduct){
//        Product savedProduct = productRepo.save(newProduct);
//        // status code 201
//        return ResponseEntity.created(linkTo(methodOn(ProductDAOController.class)
//                        // implicitly gets SELF href of the new product
//                        .getSingleProduct(savedProduct.getId())).toUri())
//                .body(productAssembler.toModel(savedProduct));
//    }
//
//    @PostMapping("/newproduct")
//    ResponseEntity<?> newProduct(@RequestBody Product newProduct){
//        try{
//            Product savedProduct = productRepo.save(newProduct);
//            EntityModel<Product> entityProduct = productAssembler.toModel(savedProduct);
//            return
//                    // status code 201
//                    ResponseEntity.created(new URI(entityProduct.getRequiredLink(IanaLinkRelations.SELF)
//                            .getHref())).body(entityProduct);
//        } catch (URISyntaxException e) {
//            return
//                    // status code: 400
//                    ResponseEntity.badRequest().body("Cannot create the product corresponding to " + newProduct);
//        }
//    }
//
//    /**
//     * a request parameter is something that we pass after the url using:
//     * "?parameterName=parameterValue
//     * If multiple request parameters are declared, separate them using &
//     * ?parameterName1=parameterValue1&?parameterName2=parameterValue2
//     * @param price is a query string
//     * @return
//     */
//    @GetMapping("/products/sale")
//    // required = true
//    CollectionModel<EntityModel<Product>> getProductsOnSale(@RequestParam(defaultValue = "1500") Double price){
//        List<EntityModel<Product>> products = productRepo.findAll()
//                .stream().filter(product -> product.getPrice()<price)
//                .sorted().map(productAssembler::toModel).collect(Collectors.toList());
//        return CollectionModel.of(products,linkTo(methodOn(ProductDAOController.class)
//                .getAllProduct()).withSelfRel());
//    }
//
//    @GetMapping("/products/optionals")
//    // required = true
//    @ResponseBody
//    String getOptionalMessage(@RequestParam Optional<String> someParameter){
//        return "someParameter value = " + someParameter.orElseGet(()->"You have not passed an argument");
//    }
//
//    @GetMapping("/products/notrequired")
//    @ResponseBody
//    String getOptionalMessage(@RequestParam (required = false) String parameter){
//        if (parameter==null) return "parameter is null";
//        return parameter;
//    }
//
//    @GetMapping("/products/ifrequired")
//    @ResponseBody
//    String getError(@RequestParam String parameter){
//        return parameter;
//    }
//
//    @GetMapping("/products/multipleparams")
//    @ResponseBody
//    String getError(@RequestParam (required = true) String param1,
//                    @RequestParam (required = false) String param2,
//                    @RequestParam (required = false) String param3){
//        return param1 + param2 + param3;
//    }
//
//    @GetMapping("/products/byname/{name}")
//    ResponseEntity<?> findProductsByName(@PathVariable String name){
//        List<EntityModel<Product>> products = productRepo.getProductsByProductName(name).stream()
//                .map(productAssembler::toModel).collect(Collectors.toList());
//        return ResponseEntity.ok().body(CollectionModel.of(products));
//    }
//
//    //TODO:
//}
