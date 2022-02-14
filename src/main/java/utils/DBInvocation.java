package utils;

import db.dao.CategoriesMapper;
import db.dao.ProductsMapper;
import db.model.CategoriesExample;
import db.model.Products;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import servises.Product;

import java.io.IOException;
import java.io.InputStream;

public class DBInvocation {
    static String resource = "";
    static SqlSessionFactory sqlSessionFactory = null;


    static {
        try {
            //NyBatis Configuration file
            String resource = "mybatis-config.xml";
            InputStream is = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static Integer countNumberOfAllCategories(String resource) throws IOException {
        CategoriesMapper categoriesMapper = getCategoriesMapper(resource);
        CategoriesExample example = new CategoriesExample();
        return Math.toIntExact(categoriesMapper.countByExample(example));
    }

    public static CategoriesMapper getCategoriesMapper(String resource) throws IOException {

        SqlSession session = sqlSessionFactory.openSession();
        return session.getMapper(CategoriesMapper.class);

    }

    public static String getTitleById(int id) throws IOException {

        CategoriesMapper categoriesMapper = getCategoriesMapper(resource);
        return categoriesMapper.selectByPrimaryKey(id).getTitle();

    }

    public static void createNewProduct(Product product) throws IOException {

        ProductsMapper productsMapper = getProductsMapper(resource);
        Products products = new Products();
        products.setId((long) (int) product.getId());
        products.setPrice(product.getPrice());
        products.setCategory_id(1L);
        products.setTitle(product.getTitle());
        System.out.println(" разделительная черта");
        System.out.println(product.getId() + " " + product.getPrice() + " " + product.getTitle());
        productsMapper.insert(products);

    }

    public static void updateProduct(Product product) throws IOException {

        ProductsMapper productsMapper = getProductsMapper(resource);
        Products products = new Products();
        products.setId((long) (int) product.getId());
        products.setPrice(product.getPrice());
        products.setCategory_id(1L);

        products.setTitle(product.getTitle());
        productsMapper.updateByPrimaryKeySelective(products);

    }

    public static void deleteProduct(int id) throws IOException {

        ProductsMapper productsMapper = getProductsMapper(resource);
//        Products products = new Products();
//        products.setId((long)(int)product.getId());
//        products.setPrice(product.getPrice());
//        products.setCategory_id(1L);
//        products.setTitle(product.getTitle());
        productsMapper.deleteByPrimaryKey((long) id);

    }

    public static Product getProductById(int id) throws IOException {

        ProductsMapper productsMapper = getProductsMapper(resource);
        Products products = productsMapper.selectByPrimaryKey((long) id);
        System.out.println(products.getId());
        Product product = new Product();

        return product.withId((int) (long) products
                        .getId())
                .withPrice(products.getPrice())
                .withTitle(products.getTitle())
                .withCategoryTitle("Food");

    }


    public static ProductsMapper getProductsMapper(String resource) throws IOException {

        SqlSession session = sqlSessionFactory.openSession();
        return session.getMapper(ProductsMapper.class);

    }
}
