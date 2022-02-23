package com.example.totalapplication.api;

public class Api {
    public static final String MOVIE_URL = "http://baobab.kaiyanapp.com/api/v4/tabs/selected?udid=11111&vc=168&vn=3.3.1&deviceModel=Huawei6&first_channel=eyepetizer_baidu_market&system_version_code=20";
    public static final String OPEN_API_BASE_URL = "https://api.apiopen.top/";
    public static final String NETEASE_MUSIC_BASE_URL="https://netease-cloud-music-api-self-tau.vercel.app/";

    /*

 //Dso Music 备用接口
API_DSO = "https://netease-cloud-music-api-lemon.vercel.app"
SONG_DETAIL_URL = "${API_DSO}/song/detail"

 // @类型 适用 NodeJs 版
 // @例子 http://music.eleuu.com/banner?type=1

API_MUSIC_ELEUU = "http://music.eleuu.com"
USER_PLAYLIST = "${API_MUSIC_ELEUU}/user/playlist"
PERSONALIZED_NEW_SONG = "${API_MUSIC_ELEUU}/personalized/newsong"
PLAYLIST_URL = "${API_MUSIC_ELEUU}/playlist/detail?id=" // 获取歌单链接
"$API_MUSIC_ELEUU/playlist/detail?id=$id"
"${API_MUSIC_ELEUU}/search?keywords=${keywords}"

API_AUTU = "https://autumnfish.cn"
    /**
     * 默认搜索关键词
     * 说明 : 调用此接口 , 可获取默认搜索关键词
     */
    //const val SEARCH_DEFAULT = "${API_AUTU}/search/default"

    /**
     * 热搜列表(详细)
     * 说明 : 调用此接口,可获取热门搜索列表
     */
    //const val SEARCH_HOT_DETAIL = "${API_AUTU}/search/hot/detail"

    /**
     * 获取歌手部分信息和歌手单曲
     * 说明 : 调用此接口 , 传入歌手 id, 可获得歌手部分信息和热门歌曲
     * 必选参数 : id: 歌手 id, 可由搜索接口获得
     * 接口地址 : /artists
     * 调用例子 : /artists?id=6452
     */
    //const val ARTISTS = "${API_AUTU}/artists"

    /**
     * 获取歌词
     * 说明 : 调用此接口 , 传入音乐 id 可获得对应音乐的歌词 ( 不需要登录 )
     * 必选参数 : id: 音乐 id
     * 接口地址 : /lyric
     * 调用例子 : /lyric?id=33894312
     * 返回数据如下图 : 获取歌词
     */
    //const val LYRIC = "${API_AUTU}/lyric"

    /**
     * 获取用户歌单
     * 说明 : 登录后调用此接口 , 传入用户 id, 可以获取用户歌单
     * 必选参数 : uid : 用户 id
     * 可选参数 :
     * limit : 返回数量 , 默认为 30
     * offset : 偏移数量，用于分页 , 如 :( 页数 -1)*30, 其中 30 为 limit 的值 , 默认为 0
     * 接口地址 : /user/playlist
     * 调用例子 : /user/playlist?uid=32953014
     */

    //歌单信息
    //val url = "$API_AUTU/playlist/detail?id=$id&cookie=${User.cookie}"

    //搜索
    //val url =

    //const val API = "${API_AUTU}/song/url?id=33894312"

    //"https://music.163.com/song/media/outer/url?id=${id}.mp3"
    //val url = "$API_AUTU/song/url?id=$id"
    //排行榜
    //private const val API = "${API_AUTU}/toplist/detail"
//    private suspend fun searchFromQQ(keywords: String): SearchSong.QQSearch? {
//        val url = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?aggr=1&cr=1&flag_qc=0&p=1&n=20&w=${keywords}"
//        val url =
//                "http://kuwo.cn/api/www/search/searchMusicBykeyWord?key=$keywords&pn=1&rn=50&httpsStatus=1&reqId=24020ad0-3ab4-11eb-8b50-cf8a98bef531"
//        val header = mapOf(
//                "Referer" to Uri.encode("http://kuwo.cn/search/list?key=$keywords"),
//                "Cookie" to "kw_token=EUOH79P2LLK",
//                "csrf" to "EUOH79P2LLK",
//                "User-Agent" to "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"
    //qq
    //play url
    //val url = """https://u.y.qq.com/cgi-bin/musicu.fcg?g_tk=5381&loginUin=0&hostUin=0&format=json&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq.json&needNewCode=0&data={"req":{"module":"CDN.SrfCdnDispatchServer","method":"GetCdnDispatch","param":{"guid":"8348972662","calltype":0,"userip":""}},"req_0":{"module":"vkey.GetVkeyServer","method":"CgiGetVkey","param":{"guid":"8348972662","songmid":["${songmid}"],"songtype":[1],"uin":"0","loginflag":1,"platform":"20"}},"comm":{"uin":0,"format":"json","ct":24,"cv":0}}""".trimIndent()
    //search
    //val url = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?aggr=1&cr=1&flag_qc=0&p=1&n=20&w=${keywords}"

    //fm
    //private const val API = "https://music.163.com/api/v1/radio/get"
    //    private const val TEST_API = "$API_AUTU/personal_fm"
    //网易云推荐歌单
    //val url = "${API_AUTU}/personalized?limit=16"

    //网易云歌单
    //private const val
    //
    //    // private const val SONG_DETAIL_URL = "https://music.163.com/api/song/detail" // 歌曲详情
    //    // private const val SONG_DETAIL_URL = "${API_AUTU}/song/detail" // 歌曲详情
    //    // private const val SONG_DETAIL_URL = "https://autumnfish.cn/song/detail" // 歌曲详情
    //    // private const val SONG_DETAIL_URL = "http://www.hjmin.com/song/detail"
    //    private const val SONG_DETAIL_URL = "http://music.eleuu.com/song/detail"
    //    private const val API = "https://music.163.com/api/v6/playlist/detail"
    //
    //    private const val SONG_DETAIL_API = "https://music.163.com/api/v3/song/detail"

    //val url = PLAYLIST_URL + playlistId + "&cookie=${User.cookie}"


    //搜索歌词
//        url = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?songmid=${songData.id}&format=json&nobase64=1"

}
