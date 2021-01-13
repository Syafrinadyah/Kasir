package id.payment.app.models;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author baros
 */
public class productModel {
    
  public int Price, Stock;
  public String Name, Code;
  
  //SETTER
  public void setCode(String code) {
    this.Code = code;
  }

  public void setName(String name) {
    this.Name = name;
  }

  public void  setPrice(int price) {
    this.Price = price;
  }
  public void  setStock(int stock) {
    this.Stock = stock;
  }

 

  //GETTER
  public String getCode() {
    return Code;
  }
  
  public int getPrice() {
    return Price;
  }
  
  public String getName() {
    return Name;
  }
  
  public int getStock() {
    return Stock;
  }
}


