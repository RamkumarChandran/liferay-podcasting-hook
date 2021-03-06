/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package de.olafkock.liferay.blogs;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;

import java.util.List;

public class ExtBlogStartup extends SimpleAction {
	/* (non-Java-doc)
	 * @see com.liferay.portal.kernel.events.SimpleAction#SimpleAction()
	 */
	public ExtBlogStartup() {
		super();
	}

	/* (non-Java-doc)
	 * @see com.liferay.portal.kernel.events.SimpleAction#run(String[] ids)
	 */
	public void run(String[] ids) throws ActionException {
		try {
			List<Company> companies = CompanyLocalServiceUtil.getCompanies();
			for(Company company: companies) {
				ExpandoTable table = ExtBlogUtil.getBlogExpandoTable(company.getCompanyId());
				long tableId = table.getTableId();
				ExtBlogUtil.getColumn(tableId, PodcastingKeys.ENCLOSURE_URL, ExpandoColumnConstants.STRING);
				ExtBlogUtil.getColumn(tableId, PodcastingKeys.ENCLOSURE_LENGTH, ExpandoColumnConstants.LONG);
				ExtBlogUtil.getColumn(tableId, PodcastingKeys.ENCLOSURE_TYPE, ExpandoColumnConstants.STRING);
				ExtBlogUtil.getColumn(tableId, PodcastingKeys.ITUNES_DURATION, ExpandoColumnConstants.STRING);
			}
		} catch (SystemException e) {
			throw new ActionException(e);
		} catch (PortalException e) {
			throw new ActionException(e);
		}
	}

}