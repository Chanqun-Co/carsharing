package io.sharing.server.core.product.application.port.inp

interface CreateProduct {

    /**
     *  상품을 등록한다.
     */
    fun create(command: CreateProductCommand)
}

class CreateProductCommand()
