package tz.co.fasthub.survey.constants;

/**
 * Created by root on 7/5/17.
 */
public class Constant {

    public static final String GW_URL="http://gravity.fasthub.co.tz:8088/fasthub/messaging/json/api";
    public static final int channel=116275;
    public static final String password="OWQzOGQ4MWRkZDg3ZWY0NmU4MDQwMWVlNTY2NDY0ZTg5MmI2YjdlOGY5OTg4ZDBjNDJiNzBmZjYyMzg3ZDZiNw==";


    public static final String oauthLink ="https://api.surveymonkey.net/oauth/authorize?response_type=code&redirect_uri=";
    public static final String redirect_uri="http://127.0.0.1:8081/survey/callback";//http://127.0.0.1:8081/survey/callback  http://survey.fasthub.co.tz:8081/survey/callback https://www.surveymonkey.com
    public static final String client_id = "g5qjzImSTsuaBTr3JcMUOw";
    public static final String client_secret="225873409898429840227397389705343753331";
    public static final String grant_type = "authorization_code";
    public static String access_token;
    public static String accessTokenFinal;
    public static String accessTokenFromPayload,expires_in, token_type;
    public static String code = null;
    public static final String complete_link="https://www.surveymonkey.com/oauth/authorize?response_type=code&redirect_uri="+redirect_uri+"&client_id="+client_id;

    public static final String access_authorized="https://api.surveymonkey.com/api_console/oauth2callback?code=";
    public static final String requestTokenUrl = "https://api.surveymonkey.net/oauth/token";

    public static final String viewSurveyUrl="https://api.surveymonkey.net/v3/surveys/118875579/details";
    public static final String responseUrl="https://api.surveymonkey.net/v3/collectors/158887354/responses";
    public static final String viewQuestionsUrl="https://api.surveymonkey.net/v3/surveys/118875579/pages/41504730/questions";
    public static final String qsnONe="https://api.surveymonkey.net/v3/surveys/118875579/pages/41504730/questions/130803708";
    //public static final String qsnOneResponse="https://api.surveymonkey.net/v3/surveys/118875579/pages/41504730/questions/130803708/response/6275141527";
    public static final String collectorUrl="https://api.surveymonkey.net/v3/surveys/118875579/collectors";//https://api.surveymonkey.net/v3/surveys/118875579/collectors
    public static final String fetchSurvey=" /surveys/118875579/responses/bulk";
    public static final String viewSurvey="https://api.surveymonkey.net/v3/surveys/118875579";

}
