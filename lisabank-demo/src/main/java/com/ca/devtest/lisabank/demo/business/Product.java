package com.ca.devtest.lisabank.demo.business;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement (name = "product")

public class Product {


        @XmlElement(name = "description")
        private String description;

        @XmlElement(name = "price")
        private BigDecimal price;
        @XmlElement(name = "createdBy")
        private String createdBy;
        public Product(){}
        public Product(String productId, String description,
                       BigDecimal price, String createdBy) {

            this.description = description;

            this.price = price;
            this.createdBy = createdBy;
        }
        @Override
        public String toString() {
            return "Product{" +

                    ",\n description='" + description + '\'' +

                    ",\n price=" + price +
                    ",\n createdBy=" + createdBy +"\n"+
                    '}';
        }
    }

