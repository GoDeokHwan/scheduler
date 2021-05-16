/**
 * 요일 반환
 * @param date 날짜
 * @return 요일
 * */
const dayOfWeek = (date) => {
  let week = ['일','월','화','수','목','금','토']
  return week[date.getDay()]
}

/**
 * 마지막 날짜
 * @param inpYear : 년 (String)
 * @param inpMonth : 월  (String)
 * @return date
 * */
const getLastDayOfMonth = (inpYear, inpMonth) => {
  let year = parseInt(inpYear);
  let month = parseInt(inpMonth);
  if(!isNull(year) && !isNull(month))
  {
    let returnDate = new Date(year, month - 1);
    return returnDate.setFullYear(returnDate.getFullYear(), returnDate.getMonth() + 1, 0);
  }
  else
  {
    return null;
  }
}
/**
 * Null 체크
 * @param obj : 문자
 * @return boolean
 * */
const isNull = (obj) => {
  return (typeof obj != "undefined" && obj != null && obj != "") ? false : true;
}

/**
 * 문자 채우기
 * @param width : 글자수
 * @param str : 문자
 * @param pad : 채울문자
 * @return 문자
 * */
const fillStr = (width, str, pad) => {
  if (!isNull(pad) && pad.length == 1 && !isNull(str)) {
    return str.length >= width ? str: new Array(width - str.length + 1).join(pad) + str
  }
}

export default {
  dayOfWeek,
  getLastDayOfMonth,
  isNull,
  fillStr
}
