export function parseCookieString(cookieString: string, domain: string, path = "/") {
    return cookieString
        .split(";")
        .map(c => c.trim())
        .map(cookie => {
            const [name, ...rest] = cookie.split("=");
            const value = rest.join("=");
            return {
                name,
                value,
                domain,
                path,
            };
        });
}

export const COOKIE_VALUE = '__ddg1_=mXgfjGWl9eWgmUA1w4vm; _ym_uid=1757338208556258668; _ym_d=1757338208; cover_preview_close=1; new_pf0=1; new_pf10=1; hidetopprjlenta=0; cookies_accepted=1; nfastpromo_x=%7B%22close%22%3A1%7D; nfastpromo_open=0; _ga_cid_uuid4=000ac055-f8a0-48e6-b195-f2e886866cf0; __utmz=83694273.1757400598.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utma=83694273.949498493.1757400598.1757405861.1757419059.3; _ym_isad=1; _ym_visorc=w; __ddg9_=5.3.189.95; XSRF-TOKEN=JUjDleJIwSRPbIWicIw19gQHk1gP6vrgYNcSTUUb; id=9183258; name=ternik2705; pwd=df6d892aea9bc47a4c9c932bcb5fb1f7; PHPSESSID=XI7xn5h1W3nMtLYsK4sM13zBXhT69xtfM87LQ6SO; __ddg10_=1757668124; __ddg8_=qeKz5cKgkHYi1kgh'