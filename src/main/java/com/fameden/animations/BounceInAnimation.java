/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fameden.animations;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.scene.Node;
import javafx.util.Duration;

public class BounceInAnimation extends TimeLineTransition {

    public BounceInAnimation(final Node node) {
        super(
                node,
                TimelineBuilder.create()
                .keyFrames(
                        new KeyFrame(Duration.millis(0),
                                new KeyValue(node.opacityProperty(), 0, Interpolator.SPLINE(0.25, 0.1, 0.25, 1)),
                                new KeyValue(node.scaleXProperty(), 0.3, Interpolator.SPLINE(0.25, 0.1, 0.25, 1)),
                                new KeyValue(node.scaleYProperty(), 0.3, Interpolator.SPLINE(0.25, 0.1, 0.25, 1))
                        ),
                        new KeyFrame(Duration.millis(500),
                                new KeyValue(node.opacityProperty(), 1, Interpolator.SPLINE(0.25, 0.1, 0.25, 1)),
                                new KeyValue(node.scaleXProperty(), 1.05, Interpolator.SPLINE(0.25, 0.1, 0.25, 1)),
                                new KeyValue(node.scaleYProperty(), 1.05, Interpolator.SPLINE(0.25, 0.1, 0.25, 1))
                        ),
                        new KeyFrame(Duration.millis(700),
                                new KeyValue(node.scaleXProperty(), 0.9, Interpolator.SPLINE(0.25, 0.1, 0.25, 1)),
                                new KeyValue(node.scaleYProperty(), 0.9, Interpolator.SPLINE(0.25, 0.1, 0.25, 1))
                        ),
                        new KeyFrame(Duration.millis(1000),
                                new KeyValue(node.scaleXProperty(), 1, Interpolator.SPLINE(0.25, 0.1, 0.25, 1)),
                                new KeyValue(node.scaleYProperty(), 1, Interpolator.SPLINE(0.25, 0.1, 0.25, 1))
                        )
                )
                .build(),
                false
        );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0.2));
    }

}
