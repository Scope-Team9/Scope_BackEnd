// const getCookie = (name) => {
//   document.cookie;
// };

const setCookie = (name, value, exp = 3) => {
  let date = new Date();
  date.setTime(date.getTime() + exp * 1000 * 60 * 60);

  document.cookie = `${name}=${value}; expires=${date.toUTCString()}; path=/`;
};

const deleteCookie = (name) => {
  document.cookie = name + "=; expires=Thu, 01 Jan 1999 00:00:10 GMT; path=/";
};

export { setCookie, deleteCookie };
