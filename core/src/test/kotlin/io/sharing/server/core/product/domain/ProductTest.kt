package io.sharing.server.core.product.domain

import io.sharing.server.core.carmodel.domain.CarModel
import io.sharing.server.core.carmodel.domain.createCarModel
import io.sharing.server.core.user.domain.Region
import io.sharing.server.core.user.domain.User
import io.sharing.server.core.user.domain.createUser
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE

class ProductTest {

    @Test
    fun `상품 생성`() {
        val user = createUser()
        val carModel = createCarModel()
        val color = ProductColor.BLACK
        val distance = 20000
        val rentalFee = 500
        val licensePlate = "사와1234"
        val region = Region.DANGSAN
        val description = "TestTest"

        val product = Product(
            user, carModel, color, distance = distance, rentalFee = rentalFee,
            licensePlate, region = region, description = description
        )

        assertThat(product.carModel).isEqualTo(carModel)
        assertThat(product.user).isEqualTo(user)
        assertThat(product.color).isEqualTo(color)
        assertThat(product.distance).isEqualTo(distance)
        assertThat(product.rentalFee).isEqualTo(rentalFee)
        assertThat(product.licensePlate).isEqualTo(licensePlate)
        assertThat(product.status).isEqualTo(ProductStatus.REGISTERED)
        assertThat(product.region).isEqualTo(region)
        assertThat(product.description).isEqualTo(description)
    }

    @Test
    fun `상품 이미지 추가`() {
        val product = createProduct()
        val images = (1..10).map { "image$it" }.toMutableList()

        product.updateImages(images)

        assertThat(product.images.size).isEqualTo(10)
        assertThat(product.images).containsExactlyInAnyOrder(
            "image1", "image2", "image3", "image4", "image5",
            "image6", "image7", "image8", "image9", "image10"
        )
    }

    @Test
    fun `등록된 상품을 승인을 할 수 있다`() {
        val product = createProduct().apply {
            this.status = ProductStatus.REGISTERED
        }

        product.approve()

        assertThat(product.status).isEqualTo(ProductStatus.AVAILABLE)
    }

    @EnumSource(value = ProductStatus::class, names = ["REGISTERED"], mode = EXCLUDE)
    @ParameterizedTest
    fun `등록 상태가 아니면 상품 승인 할 수 없다`(status: ProductStatus) {
        val product = createProduct().apply {
            this.status = status
        }

        assertThatIllegalArgumentException().isThrownBy {
            product.approve()
        }
    }

    @Test
    fun `등록된 상품을 거절할 수 있다`() {
        val product = createProduct().apply {
            this.status = ProductStatus.REGISTERED
        }

        product.reject()

        assertThat(product.status).isEqualTo(ProductStatus.REJECTED)
    }

    @EnumSource(value = ProductStatus::class, names = ["REGISTERED"], mode = EXCLUDE)
    @ParameterizedTest
    fun `등록 상태가 아니면 상품을 거절할 수 없다`(status: ProductStatus) {
        val product = createProduct().apply {
            this.status = status
        }

        assertThatIllegalArgumentException().isThrownBy {
            product.reject()
        }
    }
}

fun createProduct(
    user: User = createUser(),
    carModel: CarModel = createCarModel(),
    color: ProductColor = ProductColor.BLACK,
    distance: Int = 1000,
    rentalFee: Int = 1000,
    licensePlate: String = "사와1234",
    status: ProductStatus = ProductStatus.AVAILABLE,
    region: Region = Region.GASAN,
    description: String = "test"): Product {
    return Product(
        user, carModel, color, distance = distance, rentalFee = rentalFee,
        licensePlate, status, region = region, description = description
    )
}
