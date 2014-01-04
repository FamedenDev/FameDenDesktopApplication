/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fameden.service;

/**
 *
 * @author ravjotsingh
 */
public interface IService {
    
    public Object processRequest(Object obj);
    
    public Object populateRequest(Object obj) throws Exception;
    
}
