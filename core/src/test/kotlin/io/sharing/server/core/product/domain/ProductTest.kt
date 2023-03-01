package io.sharing.server.core.product.domain

import io.sharing.server.core.carmodel.domain.CarModel
import io.sharing.server.core.carmodel.domain.createCarModel
import io.sharing.server.core.user.domain.Region
import io.sharing.server.core.user.domain.User
import io.sharing.server.core.user.domain.createUser
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class ProductTest {

    @Test
    fun `상품 생성`() {
        val carModel = createCarModel()
        val user = createUser()
        val color = ProductColor.BLACK
        val distance = 20000
        val rentalFee = 500
        val licensePlate = "사와1234"
        val region = Region.DANGSAN
        val description = "TestTest"

        val product = Product(carModel = carModel, user = user, color = color, distance = distance, rentalFee = rentalFee, licensePlate = licensePlate, region = region, description = description)

        assertThat(product.carModel).isEqualTo(carModel)
        assertThat(product.user).isEqualTo(user)
        assertThat(product.color).isEqualTo(color)
        assertThat(product.distance).isEqualTo(distance)
        assertThat(product.rentalFee).isEqualTo(rentalFee)
        assertThat(product.licensePlate).isEqualTo(licensePlate)
        assertThat(product.status).isEqualTo(ProductStatus.REGISTERED)
        assertThat(product.region).isEqualTo(region)
        assertThat(product.description).isEqualTo(description)
        assertThat(product.images.size).isEqualTo(1)
    }

    @Test
    fun `상품 이미지 추가`() {
        val product = createProduct()
        val images = mutableListOf<String>()

        (1..10).forEach { images.add("image$it") }
        product.updateImages(images)

        assertThat(product.images.size).isEqualTo(10)
        assertThat(product.images).containsExactlyInAnyOrder(
            "image1", "image2", "image3", "image4", "image5",
            "image6", "image7", "image8", "image9", "image10"
        )
    }
}

fun createProduct(
    user: User = createUser(),
    carModel: CarModel = createCarModel(),
    color: ProductColor = ProductColor.BLACK,
    distance: Int = 1000,
    rentalFee: Int = 1000,
    licensePlate: String = "사와1234",
    region: Region = Region.GASAN,
    description: String = "test"): Product {
    return Product(
        user, carModel, color, distance = distance, rentalFee = rentalFee,
        licensePlate, region = region, description = description
    )
}
