package com.kakinohana.deliveryapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kakinohana.deliveryapi.core.validation.DeliveryTax;
import com.kakinohana.deliveryapi.core.validation.Groups;
import com.kakinohana.deliveryapi.core.validation.ZeroValueIncludesDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ZeroValueIncludesDescription(fieldValue = "deliveryTax", fieldDescription = "name", requiredDescription = "Frete Grátis")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    //@PositiveOrZero
    @DeliveryTax
    @Column(name = "delivery_tax", nullable = false)
    private BigDecimal deliveryTax;
    
    //@JsonIgnore
    //@JsonIgnoreProperties("hibernateLazyInitializer") // Tira o erro de quando vai serializar e o Lazy não deixa
    @ConvertGroup(to = Groups.CuisineId.class)
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name = "cuisine_id", nullable = false) //Faz isso caso precise colocar numa coluna com um nome diferente lá no banco
    private Cuisine cuisine;

    @JsonIgnore
    @Embedded // Indica que to incorporando essa classe
    private Address address;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime registerDate;

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateDate;


    @JsonIgnore
    @OneToMany (mappedBy = "restaurant")
    private List<Product> products;

    //@JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurant_payment_method",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

}
