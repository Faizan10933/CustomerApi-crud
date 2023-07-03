package com.faizan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String args[]) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }


    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){

    }

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);

    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id,@RequestBody NewCustomerRequest request){
        Optional<Customer> cId = customerRepository.findById(id);
        Customer customer = cId.get();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);



    }

}

//    @GetMapping("/")
//    public greetResponse greet(){
//
//        greetResponse response= new greetResponse(
//                "hey",
//                List.of("java","python","c++"),
//                new Person("faizan", 23, 42000)
//                );
//
//        return response;
//    }

//    record Person(String name, int age, double savings){
//
//    }
//    record greetResponse(
//            String greet,
//            List<String> favprogramminglanguages,
//            Person person
//    ){}
//}
