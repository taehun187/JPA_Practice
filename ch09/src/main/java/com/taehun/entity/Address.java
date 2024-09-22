package com.taehun.entity;

import java.util.Objects;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
	
	private String street;
	private String city;
	private String zipcod;
	
	@Override
	public boolean equals(Object o)
	{
		
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Address address = (Address) o;
		
		return Objects.equals(street, address.street) && Objects.equals(city,address.city)
				&&
				Objects.equals(zipcod, address.zipcod);
	}
	
	@Override
	public int hashCode() {return Objects.hash(street,city,zipcod);}

}