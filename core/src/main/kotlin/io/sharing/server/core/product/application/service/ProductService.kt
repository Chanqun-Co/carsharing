package io.sharing.server.core.product.application.service

import io.sharing.server.core.product.application.port.inp.CreateProduct
import io.sharing.server.core.product.application.port.inp.CreateProductCommand
import io.sharing.server.core.product.application.port.outp.ProductRepository
import io.sharing.server.core.support.stereotype.UseCase
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional
class ProductService(
    private val productRepository: ProductRepository
) : CreateProduct {
    override fun create(command: CreateProductCommand) {
        TODO("Not yet implemented")
    }
}
