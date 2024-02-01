package com.tritonkor.grouptester.appui;

import java.io.IOException;

/**
 * The Renderable interface defines the contract for objects that can be rendered. Classes
 * implementing this interface are expected to provide a method to render their content.
 */
public interface Renderable {

    /**
     * Renders the content of the object.
     *
     * @throws IOException If an I/O error occurs while rendering the content.
     */
    void render() throws IOException;
}
