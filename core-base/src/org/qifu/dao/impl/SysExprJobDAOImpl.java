/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package org.qifu.dao.impl;

import org.qifu.base.dao.BaseDAO;
import org.qifu.dao.ISysExprJobDAO;
import org.qifu.po.TbSysExprJob;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository("core.dao.SysExprJobDAO")
@Scope("prototype")
public class SysExprJobDAOImpl extends BaseDAO<TbSysExprJob, String> implements ISysExprJobDAO<TbSysExprJob, String> {
	
	public SysExprJobDAOImpl() {
		super();
	}
	
}
