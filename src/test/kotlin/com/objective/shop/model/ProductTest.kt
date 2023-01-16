package com.objective.shop.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.ArrayList

/**
 * created by kim
 */
internal class ProductTest {

    private lateinit var product: Product

    // Product 클래스가 들어갈 배열 생성 (배열 길이 : 0)
    private var productArray: ArrayList<Product> = ArrayList()


    /**
     * BeforeEach - 각 메서드가 실행하기 전에 실행
     */
    @BeforeEach
    fun setup() {
        product = Product("Test", 0)
        productArray.add(product)
    }

    /**
     * AfterEach - 각 메서드가 실행 후에 실행
     */
    @AfterEach
    fun clear() {
        productArray.clear()
    }

    @Test
    fun `동일한 이름의 상품 등록 불가`() {
        //given
        var newProduct = Product("Test1", 0)

        //when
        var newName = addProduct(newProduct)

        //then
        Assertions.assertThat(newName).isNotEqualTo("이미 존재하는 상품입니다.")//값이 존재 하는 경우
    }

    //TODO [등록] 서비스 영역에서 구현해야할것 같음
    private fun addProduct(product: Product): String {
        if (findByName(product.name).isPresent) {
            return "이미 존재하는 상품입니다."
        } else {
            productArray.add(product)
            return product.name
        }
    }

    //TODO [재고 조회]서비스 영역에서 구현해야할것 같음
    private fun findByName(email: String): Optional<Product> {
        return productArray.stream()
            .filter { product -> product.name.equals(email) }
            .findAny()
    }
    //TODO [재고 조회]서비스 영역에서 구현해야할것 같음
    private fun findAll(): ArrayList<Product> {
        return productArray
    }
}
