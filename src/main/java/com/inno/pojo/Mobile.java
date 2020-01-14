package com.inno.pojo;

import java.util.Objects;

public class Mobile {
    private Integer id;
    private String model;
    private Integer price;
    private String manufacturer;

    public Mobile() {
    }

    public Mobile(MobileBuilder mobileBuilder) {
        this.id = mobileBuilder.id;
        this.model = mobileBuilder.model;
        this.price = mobileBuilder.price;
        this.manufacturer = mobileBuilder.manufacturer;
    }

    public Integer getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mobile)) return false;
        Mobile mobile = (Mobile) o;
        return Objects.equals(id, mobile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class MobileBuilder {
        private Integer id;
        private String model;
        private Integer price;
        private String manufacturer;

        public MobileBuilder(Integer id) {
            this.id = id;
        }

        public MobileBuilder withModel(String model) {
            this.model = model;
            return this;
        }

        public MobileBuilder withPrice(Integer price) {
            this.price = price;
            return this;
        }

        public MobileBuilder withManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Mobile build() {
            return new Mobile(this);
        }
    }
}