//$Id: Hotel.java 5579 2007-06-27 00:06:49Z gavin $
package org.jboss.seam.example.booking;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;

@Entity
@Name("simple")
public class Simple implements Serializable
{
   private Long id;
   private String name;
   private String description;
   
   @Id @GeneratedValue
   public Long getId()
   {
      return id;
   }
   public void setId(Long id)
   {
      this.id = id;
   }
   
   @Length(max=20) @NotNull
   public String getName()
   {
      return name;
   }
   public void setName(String name)
   {
      this.name = name;
   }
   
   @Length(max=100) @NotNull
   public String getDescription()
   {
      return description;
   }
   public void setDescription(String description)
   {
      this.description = description;
   }
   
   
   @Override
   public String toString()
   {
      return "Simple(" + name + "," + description + ")";
   }
}
