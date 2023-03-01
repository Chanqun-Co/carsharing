package io.sharing.server.core.product.domain

import io.sharing.server.core.carmodel.domain.CarModel
import io.sharing.server.core.carmodel.domain.createCarModel
import io.sharing.server.core.user.domain.Region
import io.sharing.server.core.user.domain.User
import io.sharing.server.core.user.domain.createUser
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.stream.IntStream

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

        IntStream.rangeClosed(1, 10).boxed().forEach {
            product.addImage(createProductImage("test-url$it"))
        }

        assertThat(product.images.size).isEqualTo(10)
        assertThat(product.images[0].url).isEqualTo("test-url1")
    }

    fun createProduct(carModel: CarModel = createCarModel(), user: User = createUser(), color: ProductColor = ProductColor.BLACK, distance: Int = 1000, rentalFee: Int = 1000, licensePlate: String = "사와1234", region: Region = Region.GASAN, description: String = "test"): Product {
        return Product(carModel = carModel, user = user, color = color, distance = distance, rentalFee = rentalFee, licensePlate = licensePlate, region = region, description = description)
    }

    fun createProductImage(url: String = "https://s3.ap-northeast-2.amazonaws.com/carsharing/459ba1c3-6ce1-4269-b1d9-08f60f974092.jpg"): ProductImage {
        return ProductImage(url)
    }
}
