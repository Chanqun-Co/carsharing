package io.sharing.server.core.product.domain

import io.sharing.server.core.support.fixture.UserFixture
import io.sharing.server.core.user.domain.Region
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class ProductTest {

    @Test
    fun `상품 생성`() {
        val user = UserFixture.get()
        val distance = 20000
        val rentalFee = 500
        val licensePlate = "사와1234"
        val productStatus = ProductStatus.CREATED
        val region = Region.DANGSAN
        val description = "TestTest"

        val product = Product(distance, rentalFee, licensePlate, user, productStatus, region, description)

        assertThat(product.distance).isEqualTo(distance)
        assertThat(product.rentalFee).isEqualTo(rentalFee)
        assertThat(product.licensePlate).isEqualTo(licensePlate)
        assertThat(product.user).isEqualTo(user)
        assertThat(product.productStatus).isEqualTo(ProductStatus.CREATED)
        assertThat(product.region).isEqualTo(region)
        assertThat(product.description).isEqualTo(description)
    }
}
