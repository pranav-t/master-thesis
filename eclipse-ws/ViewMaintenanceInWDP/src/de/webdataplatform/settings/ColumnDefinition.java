package de.webdataplatform.settings;

public class ColumnDefinition {


		private String name;
		
		private String family;
		
		private String prefix;
		
		private Long startRange;
		
		private Long endRange;
		
		private Boolean primaryKey;
		

		public long getNumOfValues(){
			
			if(startRange == null || endRange == null)return 0;
			return endRange - startRange;
		}
		

		public ColumnDefinition(String name, String family, String prefix,
				Long startRange, Long endRange, Boolean primaryKey) {
			super();
			this.name = name;
			this.family = family;
			this.prefix = prefix;
			this.startRange = startRange;
			this.endRange = endRange;
			this.primaryKey = primaryKey;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getFamily() {
			return family;
		}

		public void setFamily(String family) {
			this.family = family;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public long getStartRange() {
			return startRange;
		}

		public void setStartRange(long startRange) {
			this.startRange = startRange;
		}

		public long getEndRange() {
			return endRange;
		}

		public void setEndRange(long endRange) {
			this.endRange = endRange;
		}

		
		
		public Boolean isPrimaryKey() {
			return (primaryKey==null)?false:primaryKey;
		}


		public void setPrimaryKey(Boolean primaryKey) {
			this.primaryKey = primaryKey;
		}


		public void setStartRange(Long startRange) {
			this.startRange = startRange;
		}


		public void setEndRange(Long endRange) {
			this.endRange = endRange;
		}



	
	

		@Override
		public String toString() {
			return "ColumnDefinition [name=" + name + ", family=" + family
					+ ", prefix=" + prefix + ", startRange=" + startRange
					+ ", endRange=" + endRange + ", primaryKey=" + primaryKey
					+ "]";
		}


		public ColumnDefinition copy(){
			
			return new ColumnDefinition(this.name, this.family,this.prefix, this.startRange, this.endRange, this.primaryKey);
			
		}
		
	

}
