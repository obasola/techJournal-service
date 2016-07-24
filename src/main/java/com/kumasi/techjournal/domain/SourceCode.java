/*
 * Created on 2 Jul 2016 ( Time 10:09:28 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.domain;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class SourceCode implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Size( max = 65 )
    private String sourceName;

    @Size( max = 16777215 )
    private String comment;


    private Date datePlaced;


    private Date dateModified;

    @NotNull
    private Integer sourceTypeId;

    @NotNull
    private Integer techNoteId;



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
    public void setSourceName( String sourceName ) {
        this.sourceName = sourceName;
    }
    public String getSourceName() {
        return this.sourceName;
    }

    public void setComment( String comment ) {
        this.comment = comment;
    }
    public String getComment() {
        return this.comment;
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

    public void setSourceTypeId( Integer sourceTypeId ) {
        this.sourceTypeId = sourceTypeId;
    }
    public Integer getSourceTypeId() {
        return this.sourceTypeId;
    }

    public void setTechNoteId( Integer techNoteId ) {
        this.techNoteId = techNoteId;
    }
    public Integer getTechNoteId() {
        return this.techNoteId;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        return this.sourceName;
    } 


}