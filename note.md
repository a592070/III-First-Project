# view
操作指令
1. service.loginService
2.  
- 一般用戶
    - 查詢股票(名稱、ID)
        - (查詢有無table)匯入資料庫
        - 顯示當月 日成交訊息(+月平均收盤價、漲幅(開盤-昨日收盤))
        - 輸入指定月份、日期 查詢該月 日成交訊息
        - 加入我的最愛(5筆)
        
    - 及時成交價(判斷是否開市)
        - 輸入股票代號
        - GUI 顯示
    - 股票總表
    - 我的最愛
- 管理員
    - 查詢用戶資料
    - 輸入SQL指令
    



# service

# dao
- init table
    - interface create table
        - create account table
            - username(一律大寫), password(區分大小寫), isadmin, favorite(1~5), register, lastupdate
        - create stockdays_ID table
            - date, tradeVolume, transAction, highestPrice, lowestPrice, openingPrice, closingPrice
        - create all_stock_id table
            - id, name, ISIN_Code, Date_Listed, Market, Industrial_Group
    - ? drop table
        - drop account table
        - drop stickdays_ID table
- insert data
    - insert admin
    - insert user
    - insert data
- update data
    - update admin
    - update user
    - update data
- delete data
    - delete admin
    - delete user
    - delete data
- dao.query data
    - dao.query table
    - dao.query admin
    - dao.query user
    - dao.query data

