//$Id: HotelSearchingAction.java 8998 2008-09-16 03:08:11Z shane.bryzak@jboss.com $
package org.jboss.seam.example.booking;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.security.Restrict;

@Stateful
@Name("simpleSearch")
@Scope(ScopeType.SESSION)
@Restrict("#{identity.loggedIn}")
public class SimpleSearchingAction implements SimpleSearching
{
    @PersistenceContext
    private EntityManager em;
    
    private String searchString;
    private int pageSize = 10;
    private int page;
    private boolean nextPageAvailable;
   
    @DataModel
    private List<Simple> simples;
   
    public void find() 
    {
        page = 0;
        querySimples();
    }

    public void nextPage() 
    {
        page++;
        querySimples();
    }
    
    private void querySimples() {
        List<Hotel> results = em.createQuery("select s from Simple s where lower(s.name) like #{pattern} or lower(h.description) like #{pattern}")
                                .setMaxResults(pageSize+1)
                                .setFirstResult(page * pageSize)
                                .getResultList();
        
        nextPageAvailable = results.size() > pageSize;
        if (nextPageAvailable) 
        {
            simples = new ArrayList<Simple>(results.subList(0,pageSize));
        } else {
            simples = results;
        }
    }

    public boolean isNextPageAvailable()
    {
        return nextPageAvailable;
    }
   
   public int getPageSize() {
      return pageSize;
   }
   
   public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
   }
   
   @Factory(value="pattern", scope=ScopeType.EVENT)
   public String getSearchPattern()
   {
      return searchString==null ? 
            "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%';
   }
   
   public String getSearchString()
   {
      return searchString;
   }
   
   public void setSearchString(String searchString)
   {
      this.searchString = searchString;
   }
   
   @Remove
   public void destroy() {}
}
