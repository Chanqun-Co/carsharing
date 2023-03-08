package io.sharing.server.core.product.application.port.outp

import io.sharing.server.core.product.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>
