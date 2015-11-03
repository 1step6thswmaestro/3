package com.soma.second.matnam.ui.models;

/**
 * Created by Dongjun on 15. 11. 2..
 */
public class Insta {

    private String id;
    private String name;
    private String url;

    public Insta(String _name, String _url) {
        this.name = _name;
        this.url = _url;
    }

    public String getId() { return this.id; }
    public void setId(String _id) { this.id = _id;  }

    public String getName() { return this.name; }
    public void setName(String _name) { this.name = _name;  }

    public String getUrl() { return this.url; }
    public void setUrl(String _url) { this.url = _url;  }

}
