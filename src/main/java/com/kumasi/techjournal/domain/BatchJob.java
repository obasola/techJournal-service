/*
 * Created on 2 Jul 2016 ( Time 10:09:28 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.domain;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class BatchJob implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Size( max = 45 )
    private String jobName;

    @Size( max = 65 )
    private String fileName;

    @Size( max = 16777215 )
    private String data;


    private Date datePlaced;


    private Date dateModified;

    @NotNull
    private Integer releaseNoteId;



    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId( Integer id ) {
        this.id = id ;
    }

    public Integer getId() {
        return this.id;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setJobName( String jobName ) {
        this.jobName = jobName;
    }
    public String getJobName() {
        return this.jobName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return this.fileName;
    }

    public void setData( String data ) {
        this.data = data;
    }
    public String getData() {
        return this.data;
    }

    public void setDatePlaced( Date datePlaced ) {
        this.datePlaced = datePlaced;
    }
    public Date getDatePlaced() {
        return this.datePlaced;
    }

    public void setDateModified( Date dateModified ) {
        this.dateModified = dateModified;
    }
    public Date getDateModified() {
        return this.dateModified;
    }

    public void setReleaseNoteId( Integer releaseNoteId ) {
        this.releaseNoteId = releaseNoteId;
    }
    public Integer getReleaseNoteId() {
        return this.releaseNoteId;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        return this.jobName;
    } 


}
