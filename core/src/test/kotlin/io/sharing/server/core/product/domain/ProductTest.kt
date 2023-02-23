package io.sharing.server.core.product.domain

import io.sharing.server.core.user.domain.User
import io.sharing.server.core.user.domain.UserStatus
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ProductTest {

    @Test
    fun `상품 생성`() {
        val email = "woogie@gmail.com"
        val firstName = "firstName"
        val lastName = "lastName"
        val birthDay = LocalDate.now()
        val user = User.create(email, firstName, lastName, birthDay)
        val distance = 20000
        val fee = 500
        val licensePlate = "사와1234"
        val productStatus = ProductStatus.CREATED
        val location = Location.Tokyo
        val description = "TestTest"

        val product = Product(distance, fee, licensePlate, user, productStatus, location, description)

        assertThat(product.distance).isEqualTo(distance)
        assertThat(product.fee).isEqualTo(fee)
        assertThat(product.licensePlate).isEqualTo(licensePlate)
        assertThat(product.user).isNotNull
        assertThat(product.productStatus).isEqualTo(ProductStatus.CREATED)
        assertThat(product.location).isEqualTo(location)
        assertThat(product.description).isEqualTo(description)
    }
}