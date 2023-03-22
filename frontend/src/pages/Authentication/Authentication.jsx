import React from "react";

import Login from "../../components/Login";
import Section from "../../components/Section/Section";

const Authentication = ({ login }) => {
  return (
    <Section id="auth" bgImage="/bg.jpg" height="100vh" opacity={0.4}>
      {login ? <Login /> : null}
    </Section>
  );
};

export default Authentication;
