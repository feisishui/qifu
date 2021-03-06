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
package org.qifu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.dao.IBaseDAO;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.SystemMessage;
import org.qifu.base.service.BaseService;
import org.qifu.dao.ISysMailHelperDAO;
import org.qifu.po.TbSysMailHelper;
import org.qifu.service.ISysMailHelperService;
import org.qifu.util.SimpleUtils;
import org.qifu.vo.SysMailHelperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("core.service.SysMailHelperService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class SysMailHelperServiceImpl extends BaseService<SysMailHelperVO, TbSysMailHelper, String> implements ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> {
	protected Logger logger=Logger.getLogger(SysMailHelperServiceImpl.class);
	private ISysMailHelperDAO<TbSysMailHelper, String> sysMailHelperDAO;
	
	public SysMailHelperServiceImpl() {
		super();
	}

	public ISysMailHelperDAO<TbSysMailHelper, String> getSysMailHelperDAO() {
		return sysMailHelperDAO;
	}

	@Autowired
	@Resource(name="core.dao.SysMailHelperDAO")
	@Required		
	public void setSysMailHelperDAO(ISysMailHelperDAO<TbSysMailHelper, String> sysMailHelperDAO) {
		this.sysMailHelperDAO = sysMailHelperDAO;
	}

	@Override
	protected IBaseDAO<TbSysMailHelper, String> getBaseDataAccessObject() {
		return sysMailHelperDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

	@Override
	public DefaultResult<List<TbSysMailHelper>> findForJobList(String mailId, String successFlag) throws ServiceException, Exception {
		if ( StringUtils.isBlank(mailId) || StringUtils.isBlank(successFlag) ) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		DefaultResult<List<TbSysMailHelper>> result = new DefaultResult<List<TbSysMailHelper>>();
		List<TbSysMailHelper> searchList = this.sysMailHelperDAO.findForJobList(mailId, successFlag);
		if (searchList!=null && searchList.size()>0) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA)) );
		}
		return result;
	}

	@Override
	public String findForMaxMailId(String mailId) throws ServiceException, Exception {
		if (StringUtils.isBlank(mailId)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}		
		return this.sysMailHelperDAO.findForMaxMailId(mailId);
	}

	@Override
	public String findForMaxMailIdComplete(String mailId) throws ServiceException, Exception {
		if (StringUtils.isBlank(mailId) || !SimpleUtils.isDate(mailId)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		String maxMailId = this.findForMaxMailId(mailId);
		if (StringUtils.isBlank(maxMailId)) {
			return mailId + "000000001";
		}
		int maxSeq = Integer.parseInt( maxMailId.substring(8, 17) ) + 1;
		if (maxSeq > 999999999) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.DATA_ERRORS) + " over max mail-id 999999999!");
		}
		return mailId + StringUtils.leftPad(String.valueOf(maxSeq), 9, "0");
	}

}
