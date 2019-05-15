package com.ca.devtest.lisabank.demo.model;


public class LisaUser {
	
	    protected String email;
	    protected String fname;
	    protected String lname;
	    protected String login;
	    protected Boolean newFlag;
	    protected String phone;
	    protected String pwd;
	    protected String ssn;
	    
	    public LisaUser(){
	    	
	    }
		/**
		 * @param email
		 * @param fname
		 * @param lname
		 * @param login
		 * @param newFlag
		 * @param phone
		 * @param pwd
		 * @param ssn
		 */
		public LisaUser(String email, String fname, String lname, String login, Boolean newFlag, String phone, String pwd,
				String ssn) {
			this.email = email;
			this.fname = fname;
			this.lname = lname;
			this.login = login;
			this.newFlag = newFlag;
			this.phone = phone;
			this.pwd = pwd;
			this.ssn = ssn;
		}

		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * @param email the email to set
		 */
		public void setEmail(String email) {
			this.email = email;
		}

		/**
		 * @return the fname
		 */
		public String getFname() {
			return fname;
		}

		/**
		 * @param fname the fname to set
		 */
		public void setFname(String fname) {
			this.fname = fname;
		}

		/**
		 * @return the lname
		 */
		public String getLname() {
			return lname;
		}

		/**
		 * @param lname the lname to set
		 */
		public void setLname(String lname) {
			this.lname = lname;
		}

		/**
		 * @return the login
		 */
		public String getLogin() {
			return login;
		}

		/**
		 * @param login the login to set
		 */
		public void setLogin(String login) {
			this.login = login;
		}

		/**
		 * @return the newFlag
		 */
		public Boolean getNewFlag() {
			return newFlag;
		}

		/**
		 * @param newFlag the newFlag to set
		 */
		public void setNewFlag(Boolean newFlag) {
			this.newFlag = newFlag;
		}

		/**
		 * @return the phone
		 */
		public String getPhone() {
			return phone;
		}

		/**
		 * @param phone the phone to set
		 */
		public void setPhone(String phone) {
			this.phone = phone;
		}

		/**
		 * @return the pwd
		 */
		public String getPwd() {
			return pwd;
		}

		/**
		 * @param pwd the pwd to set
		 */
		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

		/**
		 * @return the ssn
		 */
		public String getSsn() {
			return ssn;
		}

		/**
		 * @param ssn the ssn to set
		 */
		public void setSsn(String ssn) {
			this.ssn = ssn;
		}
		
		
}
