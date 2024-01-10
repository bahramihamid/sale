
package com.bean;

        import com.ejb.GoodsEjb;
        import com.entity.Goods;

        import javax.faces.component.UIComponent;
        import javax.faces.context.FacesContext;
        import javax.faces.convert.Converter;
        import javax.faces.convert.FacesConverter;
        import javax.naming.InitialContext;
        import javax.naming.NamingException;


@FacesConverter(forClass = Goods.class)
public class GoodConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.trim().length() == 0) {
            return null;
        }
        try {
            return ((GoodsEjb) InitialContext.doLookup("java:module/GoodsEjb")).getEm().find(Goods.class, Long.valueOf(value));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null || object.equals("")) {
            return "";
        }
        if (object instanceof Goods) {
            Goods o = (Goods) object;
           if(o.getId()==null) {return "";}
            return o.getId().toString();
        } else {
            return null;
        }
    }
}
