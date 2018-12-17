package simplicity_an.simplicity_an.HorizontalAdapters;

import java.util.ArrayList;
import java.util.List;



public class ItemModel {
    private int typeid;
    private String name;
    private String image;

    private String pdate;
    private String description;
    private String title;
    int albumcount;
    String playurl,albumimage;
    private ArrayList<String> album;
    private List<ItemModel> albumlist;

    private List<ItemModel>PhotoStoryList,VideoList;
    private String id;

    private String qtype,qtypemain;
    String ads;
    int favcount;
    String sharingurl;
    int likescount,dislikecount,commentscount,counttype;
    String shortdescription,editername;
    String youtubelink;

    public List<ItemModel> getPhotoStoryList() {
        return PhotoStoryList;
    }

    public void setPhotoStoryList(List<ItemModel> photoStoryList) {
        PhotoStoryList = photoStoryList;
    }

    public List<ItemModel> getVideoList() {
        return VideoList;
    }

    public void setVideoList(List<ItemModel> videoList) {
        VideoList = videoList;
    }

    public void setYoutubelink(String youtubelink) {
        this.youtubelink = youtubelink;
    }

    public String getYoutubelink() {
        return youtubelink;
    }

    public String getEditername() {
        return editername;
    }

    public void setEditername(String editername) {
        this.editername = editername;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getAds() {
        return ads;
    }

    public void setAds(String ads) {
        this.ads = ads;
    }
    public List<ItemModel> getAlbumlist() {
        return albumlist;
    }

    public void setAlbumlist(List<ItemModel> albumlist) {
        this.albumlist = albumlist;
    }

    public String getAlbumimage() {
        return albumimage;
    }

    public void setAlbumimage(String albumimage) {
        this.albumimage = albumimage;
    }

    public String getPlayurl() {
        return playurl;
    }

    public void setPlayurl(String playurl) {
        this.playurl = playurl;
    }

    public ArrayList<String> getAlbum() {
        return album;
    }

    public void setAlbum(ArrayList<String> album) {
        this.album = album;
    }

    public int getAlbumcount() {
        return albumcount;
    }

    public void setAlbumcount(int albumcount) {
        this.albumcount = albumcount;
    }

    public String getQtypemain() {
        return qtypemain;
    }

    public void setQtypemain(String qtypemain) {
        this.qtypemain = qtypemain;
    }

    public int getFavcount() {
        return favcount;
    }

    public void setFavcount(int favcount) {
        this.favcount = favcount;
    }

    public String getSharingurl() {
        return sharingurl;
    }

    public void setSharingurl(String sharingurl) {
        this.sharingurl = sharingurl;
    }

    public int getCommentscount() {
        return commentscount;
    }

    public int getCounttype() {
        return counttype;
    }

    public int getDislikecount() {
        return dislikecount;
    }

    public int getLikescount() {
        return likescount;
    }

    public void setCommentscount(int commentscount) {
        this.commentscount = commentscount;
    }

    public void setCounttype(int counttype) {
        this.counttype = counttype;
    }

    public void setDislikecount(int dislikecount) {
        this.dislikecount = dislikecount;
    }

    public void setLikescount(int likescount) {
        this.likescount = likescount;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getDescription(){return description;}
    public  void setDescription(String description){
        this.description=description;
    }
    public String getPdate(){return  pdate;}

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }
    public String getTitle(){return  title;}

    public void setTitle(String title) {
        this.title = title;
    }

    /******** start the Food category names****/
    public String getId(){return  id;}

    public void setId(String id) {
        this.id = id;
    }
}
