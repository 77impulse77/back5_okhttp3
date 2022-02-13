package tests;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import servises.CategoryService;
import servises.GetCategoryResponse;
import utils.DBInvocation;
import utils.RetrofitUtils;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetCategoryTest {
    static CategoryService categoryService;
    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void getCategoryByIdPositiveTest() {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @SneakyThrows
    @Test
    void getCategoryWithResponseAssertionsPositiveTest() {
        int id = 1;
        Response<GetCategoryResponse> response = categoryService.getCategory(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assert response.body() != null;
        assertThat(response.body().getId(), equalTo(id));
        String title = DBInvocation.getTitleById(id);
        System.out.println(title);
        System.out.println(id);
        assertThat(response.body().getTitle(), equalTo(title));
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo(title)));


    }

}