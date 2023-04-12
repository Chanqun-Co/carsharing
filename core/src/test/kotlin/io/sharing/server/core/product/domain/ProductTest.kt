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
        val images = mutableListOf("image")

        val product = Product(
            user, carModel, color, distance = distance, rentalFee = rentalFee,
            licensePlate, region = region, description = description, images = images
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
        assertThat(product.images).isEqualTo(images)
    }

    @Test
    fun `상품 생성 실패 - 대여료가 음수인 경우`() {
        val rentalFee = -1

        assertThatIllegalArgumentException().isThrownBy {
            createProduct(rentalFee = rentalFee)
        }
    }

    @Test
    fun `상품 생성 실패 - 등록 가능한 이미지 개수를 초과한 경우`() {
        val imageCount = Product.MAXIMUM_IMAGE_COUNT + 1
        val images = (1..imageCount).map { "image$it" }.toMutableList()

        assertThatIllegalArgumentException().isThrownBy {
            createProduct(images = images)
        }
    }

    @Test
    fun `상품 정보 수정`() {
        val product = createProduct()
        val color = ProductColor.WHITE
        val distance = 10000
        val rentalFee = 100000
        val licensePlate = "가나1234"
        val region = Region.GASAN
        val description = "updated info"
        val images = (1..Product.MAXIMUM_IMAGE_COUNT).map { "image$it" }.toMutableList()

        product.update(color, distance = distance, rentalFee = rentalFee, licensePlate, region, description, images)

        assertThat(product.color).isEqualTo(color)
        assertThat(product.distance).isEqualTo(distance)
        assertThat(product.rentalFee).isEqualTo(rentalFee)
        assertThat(product.licensePlate).isEqualTo(licensePlate)
        assertThat(product.region).isEqualTo(region)
        assertThat(product.description).isEqualTo(description)
        assertThat(product.images.size).isEqualTo(Product.MAXIMUM_IMAGE_COUNT)
        assertThat(product.images).containsExactlyInAnyOrder(
            "image1", "image2", "image3", "image4", "image5",
            "image6", "image7", "image8", "image9", "image10"
        )
    }

    @Test
    fun `상품 정보 수정 실패 - 대여료가 음수인 경우`() {
        val product = createProduct()
        val color = ProductColor.WHITE
        val distance = 10000
        val rentalFee = -1
        val licensePlate = "가나1234"
        val region = Region.GASAN
        val description = "updated info"
        val images = (1..Product.MAXIMUM_IMAGE_COUNT).map { "image$it" }.toMutableList()

        assertThatIllegalArgumentException().isThrownBy {
            product.update(color, distance = distance, rentalFee = rentalFee, licensePlate, region, description, images)
        }
    }

    @Test
    fun `상품 정보 수정 실패 - 변경 가능한 이미지 개수를 초과한 경우`() {
        val product = createProduct()
        val color = ProductColor.WHITE
        val distance = 10000
        val rentalFee = 100000
        val licensePlate = "가나1234"
        val region = Region.GASAN
        val description = "updated info"
        val imageCount = Product.MAXIMUM_IMAGE_COUNT + 1
        val images = (1..imageCount).map { "image$it" }.toMutableList()

        assertThatIllegalArgumentException().isThrownBy {
            product.update(color, distance = distance, rentalFee = rentalFee, licensePlate, region, description, images)
        }
    }

    @Test
    fun `상품 승인 성공`() {
        val product = createProduct(status = ProductStatus.REGISTERED)

        product.approve()

        assertThat(product.status).isEqualTo(ProductStatus.AVAILABLE)
    }

    @EnumSource(value = ProductStatus::class, names = ["REGISTERED"], mode = EXCLUDE)
    @ParameterizedTest
    fun `상품 승인 실패 - 등록 상태가 아닌 경우`(status: ProductStatus) {
        val product = createProduct(status = status)

        assertThatIllegalStateException().isThrownBy {
            product.approve()
        }
    }

    @Test
    fun `상품 거절 성공`() {
        val product = createProduct(status = ProductStatus.REGISTERED)

        product.reject()

        assertThat(product.status).isEqualTo(ProductStatus.REJECTED)
    }

    @EnumSource(value = ProductStatus::class, names = ["REGISTERED"], mode = EXCLUDE)
    @ParameterizedTest
    fun `상품 거절 실패 - 등록 상태가 아닌 경우`(status: ProductStatus) {
        val product = createProduct(status = status)

        assertThatIllegalStateException().isThrownBy {
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
    description: String = "test",
    images: MutableList<String> = mutableListOf()
): Product {
    return Product.create(
        user, carModel, color, distance = distance, rentalFee = rentalFee,
        licensePlate, status, region = region, description = description, images
    )
}
