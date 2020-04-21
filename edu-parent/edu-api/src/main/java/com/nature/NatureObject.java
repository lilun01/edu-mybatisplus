package com.nature;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 业务代码
 * @author wangck
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NatureObject implements Serializable {


    private static final long serialVersionUID = -7900736078070523366L;
    
    private String msg;
}