package tests;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import servises.Product;
import servises.ProductService;
import utils.RetrofitUtils;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateProductTest {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();

    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food") //Category.FOOD.title
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    @SneakyThrows
    void createProductInFoodCategoryTest() {
        Response<Product> response = productService.createProduct(product)
                .execute();
        assert response.body() != null;
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        Response<ResponseBody> response2 = productService.deleteProduct(id).execute();
        assertThat(response2.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    @SneakyThrows
    void putProduct () {
        Response<Product> response = productService.createProduct(product)
                .execute();
        assert response.body() != null;
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        Product newProduct =  new Product().withId(id).withPrice(13).withTitle("Salo").withCategoryTitle("Food");
//        System.out.println(newProduct.getId() + newProduct.getTitle());
        Response<Product> response1 = productService.putProducts(newProduct).execute();
        assertThat(response1.isSuccessful(), CoreMatchers.is(true));
        assert response1.body() != null;
        assertThat(response1.body().getPrice(), equalTo(newProduct.getPrice()));
        assertThat(response1.body().getTitle(), equalTo(newProduct.getTitle()));
        Response<ResponseBody> response2 = productService.deleteProduct(id).execute();
        assertThat(response2.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    @SneakyThrows
    void getProductList (){ //500 на сервере

        Response<ResponseBody> response = productService.getProducts()
                .execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }



}