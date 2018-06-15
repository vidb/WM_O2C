package com.webmethods.caf.is.document;

import java.io.Serializable;

/**
 * IS document wrapper
 */
public  class ScOrderToCash_Documents_CreditCheckedOrder extends java.lang.Object implements Serializable {

	
	private static final long serialVersionUID = 1L;
	// IS Document type used to generate this class
	public static final String DOCUMENT_TYPE = "ScOrderToCash.Documents:CreditCheckedOrder";
	private java.lang.String identifier;
	private java.lang.String orderDate;
	private java.lang.String orderTotal;
	private java.lang.String customerName;
	private java.lang.String vendorName;
	private java.lang.String requestedShipDate;
	private com.webmethods.caf.is.document.ScOrderToCash_Documents_CreditCheckedOrder.Address address = null;
	private java.lang.String region;
	private java.lang.String status;
	private java.lang.String comments;
	public static String[][] FIELD_NAMES = new String[][] {{"identifier", "Identifier"},{"orderDate", "OrderDate"},{"orderTotal", "OrderTotal"},{"customerName", "CustomerName"},{"vendorName", "VendorName"},{"requestedShipDate", "RequestedShipDate"},{"id", "Id"},{"address1", "Address1"},{"address2", "Address2"},{"city", "City"},{"state", "State"},{"country", "Country"},{"zip", "Zip"},{"address", "Address"},{"region", "Region"},{"status", "Status"},{"comments", "Comments"},{"part", "Part"},{"description", "Description"},{"unitPrice", "UnitPrice"},{"qty", "Qty"},{"qtyShipped", "QtyShipped"},{"lineItem", "LineItem"},
	};
	private com.webmethods.caf.is.document.ScOrderToCash_Documents_CreditCheckedOrder.LineItem[] lineItem = null;

	public ScOrderToCash_Documents_CreditCheckedOrder() {
	}

	public java.lang.String getIdentifier()  {
		
		return identifier;
	}

	public void setIdentifier(java.lang.String identifier)  {
		this.identifier = identifier;
	}

	public java.lang.String getOrderDate()  {
		
		return orderDate;
	}

	public void setOrderDate(java.lang.String orderDate)  {
		this.orderDate = orderDate;
	}

	public java.lang.String getOrderTotal()  {
		
		return orderTotal;
	}

	public void setOrderTotal(java.lang.String orderTotal)  {
		this.orderTotal = orderTotal;
	}

	public java.lang.String getCustomerName()  {
		
		return customerName;
	}

	public void setCustomerName(java.lang.String customerName)  {
		this.customerName = customerName;
	}

	public java.lang.String getVendorName()  {
		
		return vendorName;
	}

	public void setVendorName(java.lang.String vendorName)  {
		this.vendorName = vendorName;
	}

	public java.lang.String getRequestedShipDate()  {
		
		return requestedShipDate;
	}

	public void setRequestedShipDate(java.lang.String requestedShipDate)  {
		this.requestedShipDate = requestedShipDate;
	}

	/**
	 * IS document wrapper
	 */
	public static class Address extends java.lang.Object implements Serializable {
	
		
		private static final long serialVersionUID = 1L;
		private java.lang.String id;
		private java.lang.String address1;
		private java.lang.String address2;
		private java.lang.String city;
		private java.lang.String state;
		private java.lang.String country;
		public static String[][] FIELD_NAMES = new String[][] {{"id", "Id"},{"address1", "Address1"},{"address2", "Address2"},{"city", "City"},{"state", "State"},{"country", "Country"},{"zip", "Zip"},
		};
		private java.lang.String zip;
		
	
		public Address() {
		}


		public java.lang.String getId()  {
			
			return id;
		}


		public void setId(java.lang.String id)  {
			this.id = id;
		}


		public java.lang.String getAddress1()  {
			
			return address1;
		}


		public void setAddress1(java.lang.String address1)  {
			this.address1 = address1;
		}


		public java.lang.String getAddress2()  {
			
			return address2;
		}


		public void setAddress2(java.lang.String address2)  {
			this.address2 = address2;
		}


		public java.lang.String getCity()  {
			
			return city;
		}


		public void setCity(java.lang.String city)  {
			this.city = city;
		}


		public java.lang.String getState()  {
			
			return state;
		}


		public void setState(java.lang.String state)  {
			this.state = state;
		}


		public java.lang.String getCountry()  {
			
			return country;
		}


		public void setCountry(java.lang.String country)  {
			this.country = country;
		}


		public java.lang.String getZip()  {
			
			return zip;
		}


		public void setZip(java.lang.String zip)  {
			this.zip = zip;
		}
	
	}

	public com.webmethods.caf.is.document.ScOrderToCash_Documents_CreditCheckedOrder.Address getAddress()  {
		if (address == null) {
			address = new com.webmethods.caf.is.document.ScOrderToCash_Documents_CreditCheckedOrder.Address();
		}
		return address;
	}

	public void setAddress(com.webmethods.caf.is.document.ScOrderToCash_Documents_CreditCheckedOrder.Address address)  {
		this.address = address;
	}

	public java.lang.String getRegion()  {
		
		return region;
	}

	public void setRegion(java.lang.String region)  {
		this.region = region;
	}

	public java.lang.String getStatus()  {
		
		return status;
	}

	public void setStatus(java.lang.String status)  {
		this.status = status;
	}

	public java.lang.String getComments()  {
		
		return comments;
	}

	public void setComments(java.lang.String comments)  {
		this.comments = comments;
	}

	/**
	 * IS document wrapper
	 */
	public static class LineItem extends java.lang.Object implements Serializable {
	
		
		private static final long serialVersionUID = 1L;
		private java.lang.String part;
		private java.lang.String description;
		private java.lang.String unitPrice;
		private java.lang.String qty;
		public static String[][] FIELD_NAMES = new String[][] {{"part", "Part"},{"description", "Description"},{"unitPrice", "UnitPrice"},{"qty", "Qty"},{"qtyShipped", "QtyShipped"},
		};
		private java.lang.String qtyShipped;
		
	
		public LineItem() {
		}


		public java.lang.String getPart()  {
			
			return part;
		}


		public void setPart(java.lang.String part)  {
			this.part = part;
		}


		public java.lang.String getDescription()  {
			
			return description;
		}


		public void setDescription(java.lang.String description)  {
			this.description = description;
		}


		public java.lang.String getUnitPrice()  {
			
			return unitPrice;
		}


		public void setUnitPrice(java.lang.String unitPrice)  {
			this.unitPrice = unitPrice;
		}


		public java.lang.String getQty()  {
			
			return qty;
		}


		public void setQty(java.lang.String qty)  {
			this.qty = qty;
		}


		public java.lang.String getQtyShipped()  {
			
			return qtyShipped;
		}


		public void setQtyShipped(java.lang.String qtyShipped)  {
			this.qtyShipped = qtyShipped;
		}
	
	}

	public com.webmethods.caf.is.document.ScOrderToCash_Documents_CreditCheckedOrder.LineItem[] getLineItem()  {
		if (lineItem == null) {
			//TODO: create/set default value here
		}
		return lineItem;
	}

	public void setLineItem(com.webmethods.caf.is.document.ScOrderToCash_Documents_CreditCheckedOrder.LineItem[] lineItem)  {
		this.lineItem = lineItem;
	}

}