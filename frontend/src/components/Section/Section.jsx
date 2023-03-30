import React from "react";

import "./Section.css";
const Section = ({ id, dark = false, bgImage, height, opacity, children }) => {
  return (
    <section
      className={`section ${dark ? "section-dark" : ""}`}
      id={id}
      style={{ height: height }}
    >
      {bgImage && (
        <div
          className="section__bg"
          style={{
            backgroundImage: `url(${bgImage})`,
            filter: `opacity(${opacity})`,
            height: height,
          }}
        />
      )}

      {children}
    </section>
  );
};

export default Section;
